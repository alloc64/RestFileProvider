/***********************************************************************
 * Copyright (c) 2018 Milan Jaitner                                    *
 * Distributed under the MIT software license, see the accompanying    *
 * file COPYING or https://www.opensource.org/licenses/mit-license.php.*
 ***********************************************************************/

package com.alloc64.restfileprovider.server.shared.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class HashUtils
{
    public static String sha256(byte[] data)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            digest.update(data);

            return toHexString(digest.digest());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return "";
    }

    public static String sha256Base64(File file)
    {
        try
        {
            byte[] buffer = new byte[1024 * 1024];

            int count;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

            while ((count = bis.read(buffer)) > 0)
                digest.update(buffer, 0, count);

            bis.close();

            return org.bouncycastle.util.encoders.Base64.toBase64String(digest.digest());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Returns the hexadecimal representation of the given byte array.
     */
    public static String toHexString(byte[] bytes)
    {
        return toHexString(bytes, bytes.length);
    }


    /**
     * Returns the hexadecimal representation of the given byte array.
     */
    public static String toHexString(byte[] bytes, int size)
    {
        StringBuffer buffer = new StringBuffer(2*size);

        for (int index = 0; index < bytes.length; index++)
        {
            byte b = bytes[index];

            buffer.append(hexNibble(b >> 4)).append(hexNibble(b));
        }

        return buffer.toString();
    }


    /**
     * Returns the hexadecimal representation of the given nibble.
     */
    private static char hexNibble(int nibble)
    {
        nibble &= 0xf;
        return (char)(nibble < 10 ?
                '0' + nibble :
                'a' + nibble - 10);
    }
}
