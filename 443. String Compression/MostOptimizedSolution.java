class Solution { // accepted in leetcode 443.
    public int compress(char[] chars) {
        if (chars.length == 1) return 1;
        int read = 0;
        int writer = 0;
        int count = 0;
        char currentChar;
        while(read < chars.length)
        {
            currentChar = chars[read];
            count = 0;
            while(read < chars.length && currentChar == chars[read])
            {
                read++;
                count++;
            }
            chars[writer] = currentChar;
            writer++;
            if (count > 1){
            for(char c:String.valueOf(count).toCharArray())
             {
                chars[writer] = c;
                writer++;
             }
            }

        }
        
        
        return writer;
    }
}