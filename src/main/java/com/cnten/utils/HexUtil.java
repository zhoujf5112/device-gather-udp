package com.cnten.utils;

public class HexUtil {

    /**
     * 十进制数字转十六进制字符
     *
     * @return
     */
    public static String decToHexString(int decimalNum) {
        StringBuilder sb = new StringBuilder();
        while (decimalNum != 0) {
            String num = "";
            if (decimalNum >= 16) {
                int quotient = decimalNum / 16;
                num = "" + (decimalNum - 16 * quotient);
                sb.append(num);
                decimalNum = quotient;
            } else if(decimalNum >= 10) {
                switch(decimalNum) {
                    case 10:
                        num = "A";
                        sb.append(num);
                        break;
                    case 11:
                        num = "B";
                        sb.append(num);
                        break;
                    case 12:
                        num = "C";
                        sb.append(num);
                        break;
                    case 13:
                        num = "D";
                        sb.append(num);
                        break;
                    case 14:
                        num = "E";
                        sb.append(num);
                        break;
                    case 15:
                        num = "F";
                        sb.append(num);
                        break;
                }
                decimalNum = 0;
            } else {
                num = decimalNum + "";
                sb.append(num);
                decimalNum = 0;
            }
        }
        sb.reverse();
        // 不足偶数位时前面补零
        String hexString = sb.toString();
        if(hexString.length() % 2 == 1) {
            hexString = "0" + hexString;
        }
        return hexString;
    }

    /**
     * 用于建立十六进制字符的输出的小写字符数组
     */
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 用于建立十六进制字符的输出的大写字符数组
     */
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 将字节数组转换为十六进制字符数组
     *
     * @param data byte[]
     * @return 十六进制char[]
     */
    public static char[] encodeHex(byte[] data) {
        return encodeHex(data, true);
    }

    /**
     * 将字节数组转换为十六进制字符数组
     *
     * @param data        byte[]
     * @param toLowerCase <code>true</code> 传换成小写格式 ， <code>false</code> 传换成大写格式
     * @return 十六进制char[]
     */
    public static char[] encodeHex(byte[] data, boolean toLowerCase) {
        return encodeHex(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    /**
     * 将字节数组转换为十六进制字符数组
     *
     * @param data     byte[]
     * @param toDigits 用于控制输出的char[]
     * @return 十六进制char[]
     */
    protected static char[] encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param data byte[]
     * @return 十六进制String
     */
    public static String encodeHexStr(byte[] data) {
        return encodeHexStr(data, true);
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param data        byte[]
     * @param toLowerCase <code>true</code> 传换成小写格式 ， <code>false</code> 传换成大写格式
     * @return 十六进制String
     */
    public static String encodeHexStr(byte[] data, boolean toLowerCase) {
        return encodeHexStr(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param data     byte[]
     * @param toDigits 用于控制输出的char[]
     * @return 十六进制String
     */
    protected static String encodeHexStr(byte[] data, char[] toDigits) {
        return new String(encodeHex(data, toDigits));
    }

    /**
     * 将十六进制字符数组转换为字节数组
     *
     * @param data 十六进制char[]
     * @return byte[]
     * @throws RuntimeException 如果源十六进制字符数组是一个奇怪的长度，将抛出运行时异常
     */
    public static byte[] decodeHex(char[] data) {

        int len = data.length;

        if ((len & 0x01) != 0) {
            throw new RuntimeException("Odd number of characters.");
        }

        byte[] out = new byte[len >> 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f = f | toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }

        return out;
    }

    /**
     * 将十六进制字符转换成一个整数
     *
     * @param ch    十六进制char
     * @param index 十六进制字符在字符数组中的位置
     * @return 一个整数
     * @throws RuntimeException 当ch不是一个合法的十六进制字符时，抛出运行时异常
     */
    protected static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch
                    + " at index " + index);
        }
        return digit;
    }

    /**
     * @return
     */
    public static String toIntString(String hexString) {
        StringBuffer sb = new StringBuffer();
        if (hexString.length() % 2 != 0) {
            throw new RuntimeException(hexString + "的长度不是偶数位！不能转换");
        }
        for (int i = 0; i < hexString.length(); i += 2) {
            sb.append(Integer.parseInt(hexString.substring(i, i + 2), 16) + "");
        }
        return sb.toString();
    }
}
