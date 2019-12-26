package alpsha.util;

public class StringFormatter {
    public static String capitalizeAllWords (String string) {
        char[] chars = string.toCharArray();
        boolean toUpperNext = true;
        for(int i=0; i<chars.length; ++i) {
            if(Character.isWhitespace(chars[i])) {
                toUpperNext = true;
                continue;
            }
            if(toUpperNext) {
                chars[i] = Character.toUpperCase(chars[i]);
                toUpperNext = false;
            } else {
                chars[i] = Character.toLowerCase(chars[i]);
            }
        }
        return new String(chars);
    }

    public static String capitalizeWord(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        if(chars.length <= 0) {
            return "";
        }
        chars[0] = Character.toUpperCase(chars[0]);
        return String.valueOf(chars);
    }

    public static String formatVitaminName(String string) {
        //Input is: vitamin_a
        //Output is: Vitamin A

        String first = capitalizeWord(string.substring(0, 7));
        char last = Character.toUpperCase(string.charAt(8));
        return first + " " + last;
    }

}
