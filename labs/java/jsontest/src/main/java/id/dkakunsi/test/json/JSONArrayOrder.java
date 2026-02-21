package id.dkakunsi.test.json;

import org.json.JSONArray;

public class JSONArrayOrder {

    public static void main(String[] args) {
        JSONArray array = createArray();
        System.out.println(array.toString());
    }
    
    private static JSONArray createArray() {
        JSONArray array = new JSONArray();
        array.put(2);
        array.put("1");
        array.put(Integer.valueOf(3));
        return array;
    }

    /*
     * Question:
     * 
     * What is the right printed value of `array` object (could be more than one right answers):
     * 
     * * A. [1,2,3]
     * * B. ["1","2","3"]
     * * C. [2,"1",3]
     * * D. Throwing exception
     * 
     * Answer   : C
     * Reason   : 
     *            - An array is an ordered sequence of zero or more values.
     *            - JSONArray#put(Object) is receiving Object value.
     * Reference:
     *            - http://www.rfc-editor.org/rfc/rfc7159.txt
     *            - https://github.com/stleary/JSON-java/blob/master/src/main/java/org/json/JSONArray.java#L1012
     */
}