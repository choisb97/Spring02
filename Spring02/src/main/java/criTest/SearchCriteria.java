package criTest;

import lombok.Data;

@Data // setter, getter, toString 몽땅
public class SearchCriteria extends Criteria {

	private String searchType;
	private String keyword;
	
	private String[] check;
	
} // class
