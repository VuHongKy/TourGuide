package tourguide.tourguidedemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Toast;

import tourguide.tourguide.ContinueMethod;
import tourguide.tourguide.Overlay;
import tourguide.tourguide.Pointer;
import tourguide.tourguide.Sequence;
import tourguide.tourguide.ToolTip;
import tourguide.tourguide.TourGuide;

/**
 * InSequenceActivity demonstrates how to use TourGuide in sequence one after another
 *
 * Test it with scenarios below:
 * 1) if the continue method is Overlay, and both of defaultOverlay's OnClicklistener
 *    and Overlay's OnClickListener is set, compiler will throw an error at runtime
 *
 * 2) If the one of the tourGuide is not set, it will follow the settings of default Overlay.
 *
 * 3) Warning message will be shown if both of the overlay's onClickListener is not set
 *
 * 4) If the continueMethod is null and overlay is not set, it will follow the default Overlay method.
 *    If default overlay and overlay are set, it will not throw the runTimeErrorException and it
 *    will follow overlay's method.
 *    .
 */
public class InSequenceWithoutContinueMethodActivity extends ActionBarActivity {
    public TourGuide mTutorialHandler;
    public Activity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_in_sequence);

        /* Get 3 buttons from layout */
        Button button = (Button)findViewById(R.id.button);
        final Button button2 = (Button)findViewById(R.id.button2);
        final Button button3 = (Button)findViewById(R.id.button3);

        /* setup enter and exit animation */
        Animation enterAnimation = new AlphaAnimation(0f, 1f);
        enterAnimation.setDuration(600);
        enterAnimation.setFillAfter(true);

        Animation exitAnimation = new AlphaAnimation(1f, 0f);
        exitAnimation.setDuration(600);
        exitAnimation.setFillAfter(true);

        // the return handler is used to manipulate the cleanup of all the tutorial elements
        TourGuide mTG1 = TourGuide.init(this)
                .setToolTip(new ToolTip()
                        .setTitle("Hey!")
                        .setDescription("I'm the top fellow")
                        .setGravity(Gravity.RIGHT))
                .setOverlay(new Overlay().setEnterAnimation(enterAnimation).setExitAnimation(exitAnimation).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(InSequenceWithoutContinueMethodActivity.this, "Hihi! Nice to meet you", Toast.LENGTH_SHORT).show();
                        mTutorialHandler.next();
                    }
                }))
                .playLater(button);


        TourGuide mTG2 = TourGuide.init(this)
                .playLater(button2);

        TourGuide mTG3 = TourGuide.init(this)
                .setToolTip(new ToolTip()
                        .setTitle("Hey!")
                        .setDescription("It's time to say goodbye")
                        .setGravity(Gravity.TOP | Gravity.RIGHT))
                .setOverlay(new Overlay().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTutorialHandler.next();
                    }
                }))
                .playLater(button3);

        Sequence seq = new Sequence.SequenceBuilder()
                             .add(mTG1, mTG2, mTG3)
                             .setDefaultOverlay(new Overlay()
                                     .setEnterAnimation(enterAnimation).setExitAnimation(exitAnimation)
                                     .setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             Toast.makeText(InSequenceWithoutContinueMethodActivity.this, "Hihi! This is the default Overlay", Toast.LENGTH_SHORT).show();
                                             mTutorialHandler.next();
                                         }
                                     }))
                             .setDefaultToolTip(new ToolTip()
                                     .setTitle("Hey!")
                                     .setDescription("Just the middle man")
                                     .setGravity(Gravity.BOTTOM | Gravity.LEFT)
                                     .setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             Toast.makeText(InSequenceWithoutContinueMethodActivity.this, "Bello! This is the default Tooltip!", Toast.LENGTH_SHORT).show();
                                         }
                                     }))
                             .setDefaultPointer(new Pointer())
                             .setDisableButton(true)
                             .build();


        mTutorialHandler = TourGuide.init(this)
                             .playInSequence(seq);

        /* setup 1st button, when clicked, cleanUp() and re-run TourGuide on button2 */
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mTutorialHandler.next();
            }
        });

        /* setup 2nd button, when clicked, cleanUp() and re-run TourGuide on button3 */
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mTutorialHandler.next();
            }
        });

        /* setup 3rd button, when clicked, run cleanUp() */
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mTutorialHandler.cleanUp();
            }
        });

    }
}