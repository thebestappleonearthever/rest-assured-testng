package utility;

import java.util.List;

public class ResponseValidator {

    public static boolean containsSpecificText(List<String> responseList, String searchText) {

        for (String response: responseList){
            if(response.contains(searchText)){
                return true;
            }
        }
        return false;
    }




}
