package me.feehgamer.msc.bungee.utils;

import net.md_5.bungee.api.ChatColor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class TextUtils {
    private static final List<Charset> supportedCharsets = new ArrayList();

    public TextUtils() {
    }

    public static String formatText(String text) {
        return formatText(text, false);
    }

    public static String formatText(String text, boolean capitalize) {
        if (text != null && !text.equals("")) {
            if (capitalize) {
                text = text.substring(0, 1).toUpperCase() + text.substring(1);
            }

            return ChatColor.translateAlternateColorCodes('&', text);
        } else {
            return "";
        }
    }

    public static List<String> formatText(List<String> list) {
        return (List)list.stream().map(me.feehgamer.msc.bungee.utils.TextUtils::formatText).collect(Collectors.toList());
    }

    public static List<String> formatText(String... list) {
        return (List)Arrays.stream(list).map(me.feehgamer.msc.bungee.utils.TextUtils::formatText).collect(Collectors.toList());
    }

    public static List<String> wrap(String line) {
        return wrap((String)null, line);
    }

    public static List<String> wrap(String color, String line) {
        if (color != null) {
            color = "&" + color;
        } else {
            color = "";
        }

        List<String> lore = new ArrayList();
        int lastIndex = 0;

        for(int n = 0; n < line.length(); ++n) {
            if (n - lastIndex >= 25 && line.charAt(n) == ' ') {
                lore.add(formatText(color + formatText(line.substring(lastIndex, n))));
                lastIndex = n;
            }
        }

        if (lastIndex - line.length() < 25) {
            lore.add(formatText(color + formatText(line.substring(lastIndex))));
        }

        return lore;
    }

    public static String convertToInvisibleLoreString(String s) {
        if (s != null && !s.equals("")) {
            StringBuilder hidden = new StringBuilder();
            char[] var2 = s.toCharArray();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                char c = var2[var4];
                hidden.append('§').append(';').append('§').append(c);
            }

            return hidden.toString();
        } else {
            return "";
        }
    }

    public static String convertToInvisibleString(String s) {
        if (s != null && !s.equals("")) {
            StringBuilder hidden = new StringBuilder();
            char[] var2 = s.toCharArray();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                char c = var2[var4];
                hidden.append('§').append(c);
            }

            return hidden.toString();
        } else {
            return "";
        }
    }

    public static String convertFromInvisibleString(String s) {
        return s != null && !s.equals("") ? s.replaceAll("§;§|§", "") : "";
    }

    public static Charset detectCharset(File f, Charset def) {
        byte[] buffer = new byte[2048];

        int len;
        try {
            FileInputStream input = new FileInputStream(f);

            try {
                len = input.read(buffer);
            } catch (Throwable var8) {
                try {
                    input.close();
                } catch (Throwable var7) {
                    var8.addSuppressed(var7);
                }

                throw var8;
            }

            input.close();
        } catch (Exception var9) {
            return null;
        }

        return len != -1 ? detectCharset(buffer, len, def) : def;
    }

    public static Charset detectCharset(BufferedInputStream reader, Charset def) {
        byte[] buffer = new byte[2048];

        int len;
        try {
            reader.mark(2048);
            len = reader.read(buffer);
            reader.reset();
        } catch (Exception var5) {
            return null;
        }

        return len != -1 ? detectCharset(buffer, len, def) : def;
    }

    public static Charset detectCharset(byte[] data, int len, Charset def) {
        if (len > 4) {
            if (data[0] == -1 && data[1] == -2) {
                return StandardCharsets.UTF_16LE;
            }

            if (data[0] == -2 && data[1] == -1) {
                return StandardCharsets.UTF_16BE;
            }

            if (data[0] == -17 && data[1] == -69 && data[2] == -65) {
                return StandardCharsets.UTF_8;
            }
        }

        int newLen;
        for(newLen = len; newLen > 0 && !Character.isWhitespace(data[newLen - 1]); --newLen) {
        }

        if (len > 512 && newLen < 512) {
            newLen = len;
        }

        ByteBuffer bBuff = ByteBuffer.wrap(data, 0, newLen).asReadOnlyBuffer();
        Iterator var5 = supportedCharsets.iterator();

        while(var5.hasNext()) {
            Charset charset = (Charset)var5.next();
            if (charset != null && isCharset(bBuff, charset)) {
                return charset;
            }

            bBuff.rewind();
        }

        return def;
    }

    public static boolean isCharset(ByteBuffer data, Charset charset) {
        CharsetDecoder decoder = charset.newDecoder();
        decoder.onMalformedInput(CodingErrorAction.REPORT);
        decoder.onUnmappableCharacter(CodingErrorAction.REPORT);
        return decoder.decode(data, CharBuffer.allocate(data.capacity()), true).isUnderflow();
    }

    static {
        supportedCharsets.add(StandardCharsets.UTF_8);
        supportedCharsets.add(StandardCharsets.ISO_8859_1);

        try {
            supportedCharsets.add(Charset.forName("windows-1253"));
            supportedCharsets.add(Charset.forName("ISO-8859-7"));
        } catch (Exception var1) {
        }

        supportedCharsets.add(StandardCharsets.US_ASCII);
    }
}
