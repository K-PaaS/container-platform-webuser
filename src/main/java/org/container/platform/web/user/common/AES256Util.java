package org.container.platform.web.user.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;

/**
 * paastaDeliveryPipelineApi
 * paasta.delivery.pipeline.api.common
 *
 * @author REX
 * @version 1.0
 * @since 8 /3/2017
 */
public class AES256Util {

    private static final Logger LOGGER = LoggerFactory.getLogger(AES256Util.class);
    private static final String CHAR_SET_NAME = "UTF-8";
    private static final String AES256_KEY = "AES256-KEY-MORE-THAN-16-LETTERS";
    private String ivParameterSpec;
    private Key secretKeySpec;


    /**
     * Instantiates a new Aes 256 util.
     */
    public AES256Util() {
        try {
            String key = AES256_KEY;
            this.ivParameterSpec = key.substring(0, 16);

            byte[] keyBytes = new byte[16];
            byte[] aes256KeyBytes = key.getBytes(CHAR_SET_NAME);
            int aes256KeyBytesLength = aes256KeyBytes.length;

            if (aes256KeyBytesLength > keyBytes.length) {
                aes256KeyBytesLength = keyBytes.length;
            }

            System.arraycopy(aes256KeyBytes, 0, keyBytes, 0, aes256KeyBytesLength);
            this.secretKeySpec = new SecretKeySpec(keyBytes, "AES");

        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Exception :: AES256Util :: UnsupportedEncodingException :: {}", e);
        }
    }




}

