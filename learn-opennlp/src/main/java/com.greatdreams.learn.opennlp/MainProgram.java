package com.greatdreams.learn.opennlp;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainProgram {
    public static Logger logger = LoggerFactory.getLogger(MainProgram.class);
    public static void main(String []args) {
        logger.info("learn opennlp, application begins to run...");
        try(InputStream modelIn = new FileInputStream("src/main/resources/en-token.bin")) {
            TokenizerModel model = new TokenizerModel(modelIn);
            Tokenizer tokenizer = new TokenizerME(model);
            String tokens[] = tokenizer.tokenize("An input sample snetences 中文");
            logger.info("tokens length: " + tokens.length);
            StringBuffer tokensInfo = new StringBuffer();
            for(String token: tokens) {
                tokensInfo.append(token + ",");
            }
            logger.info(tokensInfo.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("application exits!");
    }
}