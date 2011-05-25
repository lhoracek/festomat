package cz.festomat.client.data;

import java.util.List;

import cz.festomat.client.data.beans.CommentBean;
import cz.festomat.client.data.beans.FestivalBean;
import cz.festomat.client.data.beans.FestivalListBean;

public interface IDataSource {

	List<FestivalListBean> getAllFestivalls();
	
	FestivalBean getFestivalById(String id);
	
	List<CommentBean> getAllComments(String festivalId);
	
	void sendComment(String festivalId, CommentBean comment);
}
