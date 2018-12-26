final class TextInput {

    public static String method525(int i, Buffer stream) {
        int j = 0;
        for (int l = 0; l < i; l++) {
            int i1 = stream.readUnsignedByte(); //recieved from server
            aCharArray631[j++] = validChars[i1];
        }

        boolean flag1 = true;
        for (int k1 = 0; k1 < j; k1++) {
            char c = aCharArray631[k1];
            if (flag1 && c >= 'a' && c <= 'z') {
                aCharArray631[k1] += '\uFFE0';
                flag1 = false;
            }
            if (c == '.' || c == '!' || c == '?')
                flag1 = true;
        }

        return new String(aCharArray631, 0, j);
    }

    public static void method526(String text, Buffer stream) {
        if (text.length() > 80)
            text = text.substring(0, 80);
        for (int charAt = 0; charAt < text.length(); charAt++) {
            char c = text.charAt(charAt);
            for (int index = 0; index < validChars.length; index++) {
                if (c == validChars[index]) {
                	stream.writeWordBigEndian(index); //sending to server
                	break;
                }
            }
        }
    }
    
    public static String processText(String s) {
        buffer.currentOffset = 0;
        method526(s, buffer);
        int j = buffer.currentOffset;
        buffer.currentOffset = 0;
        String s1 = method525(j, buffer);
        return s1;
    }

    private static final char[] aCharArray631 = new char[100];
    private static final Buffer buffer = new Buffer(new byte[100]);
    private static char validChars[] = {
        ' ', 'e', 't', 'a', 'o', 'i', 'h', 'n', 's', 'r',
        'd', 'l', 'u', 'm', 'w', 'c', 'y', 'f', 'g', 'p',
        'b', 'v', 'k', 'x', 'j', 'q', 'z','E', 'T', 'A', 'O', 'I', 'H', 'N', 'S', 'R',
        'D', 'L', 'U', 'M', 'W', 'C', 'Y', 'F', 'G', 'P',
        'B', 'V', 'K', 'X', 'J', 'Q', 'Z', '0', '1', '2',
        '3', '4', '5', '6', '7', '8', '9', ' ', '!', '?',
        '.', ',', ':', ';', '(', ')', '-', '&', '*', '\\',
        '\'', '@', '#', '+', '=', '\243', '$', '%', '"', '[',
        ']', '>', '<', '^', '/', '{', '}', '|',
};



}