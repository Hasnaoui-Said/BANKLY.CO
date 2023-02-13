package next.bankly.co.operations.rest.provided.facade;

import next.bankly.co.operations.rest.required.vo.Post;
import next.bankly.co.operations.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.endpoint}/post")
public class PostTest {
    @Autowired
    PostService postService;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public List<Post> getPosts() {
        return postService.getPosts();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/id/{id}")
    public Post getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }
}
