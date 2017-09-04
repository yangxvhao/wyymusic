package com.example.demo.pipeline;

import com.example.demo.model.Comment;
import com.example.demo.model.Music;
import com.example.demo.service.CommentService;
import com.example.demo.service.MusicService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;


import java.util.List;
import java.util.Map;

/**
 * Created by Ezio on 2017/6/28.
 */
@Component
public class NetEaseMusicPipeline implements Pipeline {

	@Autowired(required = false)
	public MusicService mMusicDao;

	@Autowired
	public CommentService mCommentDao;

	@Override
	public void process(ResultItems resultItems, Task task) {

		for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
			if (entry.getKey().equals("music")) {
				Music music = (Music) entry.getValue();
				mMusicDao.insertMusic(music);

			} else {
				List<Comment> comment = (List<Comment>) entry.getValue();
				mCommentDao.insertBatch(comment);

			}

		}
	}


}
