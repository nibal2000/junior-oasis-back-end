package com.example.junioroasisbackend;
import com.example.junioroasisbackend.dtos.requests.PostRequestDTO;
import com.example.junioroasisbackend.dtos.responses.PostResponseDTO;
import com.example.junioroasisbackend.entities.Post;
import com.example.junioroasisbackend.entities.User;
import com.example.junioroasisbackend.repositories.PostRepository;
import com.example.junioroasisbackend.services.posts.PostServiceImpl;
import com.example.junioroasisbackend.services.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {
    @Mock
    private UserService userService;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    private User user;
    private PostRequestDTO postRequestDTO;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);

        postRequestDTO = new PostRequestDTO();
        postRequestDTO.setTitle("Test Post");
        postRequestDTO.setBody("This is a test post.");
        postRequestDTO.setTags(Arrays.asList("test", "java"));
    }

    @Test
    public void testAddPost_Success() {
        when(userService.getCurrentUser()).thenReturn(user);
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PostResponseDTO result = postService.addPost(postRequestDTO);

        assertNotNull(result);
        assertEquals(postRequestDTO.getTitle(), result.getTitle());
        assertEquals(postRequestDTO.getBody(), result.getBody());
        assertEquals(postRequestDTO.getTags(), result.getTags());
    }
}
