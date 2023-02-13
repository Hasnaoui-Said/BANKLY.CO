package next.bankly.co.operations.service;

import next.bankly.co.operations.rest.required.facade.JSONPlaceHolderClient;
import next.bankly.co.operations.rest.required.vo.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    JSONPlaceHolderClient jsonPlaceHolderClient;

    public List<Post> getPosts() {
        return jsonPlaceHolderClient.getPosts();
    }

    public Post getPostById(Long postId) {
        return jsonPlaceHolderClient.getPostById(postId);
    }
}
