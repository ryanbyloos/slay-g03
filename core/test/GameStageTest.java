
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.*;
import org.mockito.Mockito;


public class GameStageTest {

    private static HeadlessApplication application;
    private Contact contact;
    private World world;
    private SpriteBatch batch;
    private Animation animationMock;
    private TextureRegion textureMock;


    @BeforeClass
    public static void init() {
        application = new HeadlessApplication(new ApplicationListener() {
            @Override
            public void create() {
            }

            @Override
            public void resize(int width, int height) {
            }

            @Override
            public void render() {
            }

            @Override
            public void pause() {
            }

            @Override
            public void resume() {
            }

            @Override
            public void dispose() {
            }
        });
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl = Gdx.gl20;
    }

    @Before
    public void setUp() {
        contact = Mockito.mock(Contact.class);
        world = new World(Vector2.Zero, true);
        batch = Mockito.mock(SpriteBatch.class);
        animationMock = Mockito.mock(Animation.class);
        textureMock = Mockito.mock(TextureRegion.class);
    }


    @AfterClass
    public static void cleanup() {
        application.exit();
    }
}