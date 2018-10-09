package com.greatdreams.learn.netty.server

import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.util.ReferenceCountUtil
import javaslang.collection.List
import org.slf4j.LoggerFactory
import java.nio.ByteBuffer
import java.util.*

class DiscardServer(val port: Int = 10000) {
    companion object {
        val logger = LoggerFactory.getLogger(DiscardServer.javaClass)

        @JvmStatic
        fun main(args: Array<String>) {
            val port = 9000
            // create discard server and run it
            DiscardServer(port).run()
        }
    }
    fun run() {
        val boosGroup = NioEventLoopGroup()
        val workerGroup = NioEventLoopGroup()

        try {
            val b = ServerBootstrap()
            b.group(boosGroup, workerGroup)
                    .channel(NioServerSocketChannel::class.java)
                    .childHandler(object: ChannelInitializer<SocketChannel>() {
                        override fun initChannel(ch: SocketChannel?) {
                            ch!!.pipeline().addLast(DiscardServerHandler())
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)

            // bind and start to accept incoming connections
            val f = b.bind(port).sync()
            // wait until the server socket is closed
            // in this example, this does not happen, but you can do that to gracefully
            // shut down your server
            f.channel().closeFuture().sync()
        }catch (e: Exception) {
            logger.warn(e.toString())
        }finally {
            workerGroup.shutdownGracefully()
            boosGroup.shutdownGracefully()
        }
    }
}

class DiscardServerHandler: ChannelInboundHandlerAdapter() {
    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        val input: ByteBuf? = msg as ByteBuf

        val bytes = LinkedList<Byte>()
        try {
            while(input!!.isReadable()) {
                bytes.add(input.readByte())
            }
            val lines = String(bytes.toByteArray(), Charsets.UTF_8)
            println(lines)
        }finally {
            // discard the received data silently
            ReferenceCountUtil.release(msg)
        }

        val outValue = ctx!!.alloc().buffer(10)
        outValue.writeBytes("discard server\n".toByteArray(Charsets.UTF_8))
        ctx.write(outValue)
        ctx.flush()
    }
}