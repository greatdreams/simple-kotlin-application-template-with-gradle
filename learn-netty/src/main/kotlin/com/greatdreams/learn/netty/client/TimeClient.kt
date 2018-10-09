package com.greatdreams.learn.netty.client

import io.netty.bootstrap.Bootstrap
import io.netty.buffer.ByteBuf
import io.netty.channel.*
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.ByteToMessageDecoder
import io.netty.handler.codec.MessageToByteEncoder
import org.slf4j.LoggerFactory
import java.util.*

class TimeClient {
    companion object {
        val logger = LoggerFactory.getLogger(TimeClient.javaClass)
        @JvmStatic fun main(args: Array<String>) {
            val host = "127.0.0.1"
            val port = 9000

            val workerGroup = NioEventLoopGroup()

            try {
                val b = Bootstrap()
                b.group(workerGroup)
                        .channel(NioSocketChannel::class.java)
                        .option(ChannelOption.SO_KEEPALIVE, true)
                        .handler(object: ChannelInitializer<SocketChannel>() {
                            override fun initChannel(ch: SocketChannel?) {
                                ch!!.pipeline().addLast(TimeDecoder(), TimeClientHandler())
                            }
                        })

                // Start the client
                val f = b.connect(host, port).sync()

                // wait until the connection is closed
                f.channel().closeFuture().sync()
            }finally {
                workerGroup.shutdownGracefully()
            }
        }
    }
}

class TimeClientHandler: ChannelInboundHandlerAdapter() {
    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
       // val m = msg as ByteBuf
        try {
            // val currentTimeMills = (m.readUnsignedInt() - 2208988800L) * 1000L
            //println(Date(currentTimeMills))
            val m = msg as UnixTime
            println(m)
            ctx!!.close()
        }finally {
            // m.release()
        }
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {
        cause!!.printStackTrace()
        ctx!!.close()
    }
}

class TimeDecoder: ByteToMessageDecoder() {
    override fun decode(ctx: ChannelHandlerContext?, `in`: ByteBuf?, out: MutableList<Any>?) {
        if (`in`!!.readableBytes() < 4) {
            return
        }
        out!!.add(UnixTime(`in`.readUnsignedInt()))
    }
}

data class UnixTime(val value: Long = System.currentTimeMillis() / 1000L + 2208988800L) {

    override fun toString(): String {
        return Date((value - 2208988800L) * 1000L).toString()
    }
}

class TimeEncoder: MessageToByteEncoder<UnixTime>() {
    override fun encode(ctx: ChannelHandlerContext?, msg: UnixTime?, out: ByteBuf?) {
        out!!.writeLong(msg!!.value)
    }

}

class TimeEncode1: ChannelOutboundHandlerAdapter() {
    override fun write(ctx: ChannelHandlerContext?, msg: Any?, promise: ChannelPromise?) {
        val m = msg as UnixTime
        val encoded = ctx!!.alloc().buffer(4)
        encoded.writeLong(m.value)
        ctx.write(encoded, promise)
    }
}