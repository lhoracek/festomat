package cz.festomat.client.data;

import java.util.List;
import java.util.Map;

public interface IDataSource {

	Map<String, String> getAllFestivalls();
	
	FestivalBean getFestivalById(String id);
	
	List<CommentBean> getAllComments(String festivalId);
	
	void sendComment(String festivalId, CommentBean comment);
}
