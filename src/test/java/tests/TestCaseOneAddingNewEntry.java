package tests;
import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import models.apiModels.Post;
import models.apiModels.User;
import org.junit.jupiter.api.Test;
import aquality.selenium.core.logging.Logger;
import org.testng.Assert;
import utils.*;
import java.util.*;
import java.util.stream.Collectors;
import static com.google.common.collect.MoreCollectors.onlyElement;
import static utils.JsonUtils.setPost;
import static utils.JsonUtils.setUser;

class TestCaseOneAddingNewEntry extends BaseTestCaseOne {

    private static final ISettingsFile TEST_DATA = new JsonSettingsFile("testData.json");
    private static final int VALID_POST_ID = Integer.parseInt(TEST_DATA.getValue("/validPostId").toString());
    private static final int INVALID_POST_ID = Integer.parseInt(TEST_DATA.getValue("/invalidPostId").toString());
    private static final int USER_ID = Integer.parseInt(TEST_DATA.getValue("/userId").toString());
    private static final int VALID_USER_ID = Integer.parseInt(TEST_DATA.getValue("/validUserId").toString());
    private static final String POSTS_URI = TEST_DATA.getValue("/postsURI").toString();
    private static final String USER_URI = TEST_DATA.getValue("/usersURI").toString();
    private final Logger logger = AqualityServices.getLogger();
    public final RequestSteps request = new RequestSteps();
    private static Post expectedPost = new Post();
    private static Post actualPost = new Post();
    private static User expectedUser = new User();

    @Test
    void getPostsAndSortById() {
        logger.info("Getting list of all posts and sort by id");
        List<Post> post = request.getAllResources(POSTS_URI, Post.class);
        List<Post> actualPost = post.stream()
                .sorted(Comparator.comparingInt(Post::getId))
                .collect(Collectors.toList());
        Assert.assertEquals(post, actualPost, "Posts are not sorted");
    }

    @Test
    void getValidPostId() {
        logger.info("Getting post with id %s", VALID_POST_ID);
        expectedPost = setPost(VALID_POST_ID);
        Assert.assertEquals(expectedPost, request.getValidResource(POSTS_URI, VALID_POST_ID, Post.class), "Posts are not equal");
    }

    @Test
    void invalidPostId() {
        logger.info("Getting post with id %s", INVALID_POST_ID);
        Assert.assertNotNull(request.getInvalidResource(POSTS_URI, INVALID_POST_ID, Post.class), "Post is not valid");
    }

    @Test
    void sendPost() {
        logger.info("Sending post");
        expectedPost.setBody(RandomUtil.getRandomString());
        expectedPost.setTitle(RandomUtil.getRandomString());
        expectedPost.setUserId(USER_ID);
        actualPost = request.createResource(POSTS_URI, expectedPost, Post.class);
        Assert.assertEquals(expectedPost.getBody(), actualPost.getBody(), "Bodies are not equal");
        Assert.assertEquals(expectedPost.getTitle(), actualPost.getTitle(), "Titles are not equal");
        Assert.assertEquals(expectedPost.getUserId(), actualPost.getUserId(), "UserId not equal");
        Assert.assertFalse(String.valueOf(actualPost.getId()).isEmpty(), "Id is empty");
    }

    @Test
    void getUsersList() {
        logger.info("Getting users list");
        expectedUser = setUser(VALID_USER_ID);
        List<User> userList = request.getAllResources(USER_URI, User.class);
        Assert.assertEquals(userList.stream()
                .filter(x -> x.getId() == VALID_USER_ID)
                .collect(onlyElement()), expectedUser, "Users are not equal");
    }

    @Test
    void getUserId() {
        logger.info("Getting userId %s", VALID_USER_ID);
        expectedUser = setUser(VALID_USER_ID);
        Assert.assertEquals(expectedUser, request.getValidResource(USER_URI, VALID_USER_ID, User.class), "Users are not equal");
    }
}