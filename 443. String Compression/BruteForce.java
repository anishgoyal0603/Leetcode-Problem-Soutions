class Solution {
    public int compress(char[] chars) {
        if (chars.length == 1) return 1;
        StringBuilder sb = new StringBuilder("");
        int count = 1;
        int i;
        for (i=0;i<chars.length-1;i++)
        {
            if(chars[i] == chars[i+1])
            {
                count++;
            }
            else{
                sb.append(chars[i]);
                if(count > 1)
                sb.append(count);
                count = 1;
            }
        }
         sb.append(chars[i]);
                if(count > 1)
                sb.append(count);
                count = 1;
        for (int j=0;j<sb.length();i++)
        {
            chars[j] = sb.charAt(j);
        }
        return sb.length();
    }
}