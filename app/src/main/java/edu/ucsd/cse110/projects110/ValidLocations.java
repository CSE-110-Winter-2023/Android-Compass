package edu.ucsd.cse110.projects110;public

class ValidLocations {
    public static Double minLong =(double)-180;
    public static Double maxLong =(double)180;
    public static boolean isValidDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidLong(String str){
        String newStr=str;
        if(newStr.endsWith("\u00B0")){
            newStr=newStr.substring(0,newStr.length() - 1);
        }
        if(isValidDouble(newStr)){
            if(Double.parseDouble(newStr)>=(minLong)&&
                    Double.parseDouble(newStr)<=(maxLong)){
                return true;
            }
        }
        return false;
    }

    public static boolean isValidLat(String str){
        String newStr=str;
        if(newStr.endsWith("\u00B0")){
            newStr=newStr.substring(0,newStr.length() - 1);
        }
        if(isValidDouble(newStr)){
            if(Double.parseDouble(newStr)>=(minLong/2)&&
                    Double.parseDouble(newStr)<=(maxLong/2)){
                return true;
            }
        }
        return false;
    }

}
