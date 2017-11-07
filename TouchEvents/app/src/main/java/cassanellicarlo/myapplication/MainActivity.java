package cassanellicarlo.myapplication;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static android.view.MotionEvent.actionToString;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, View.OnClickListener, View.OnLongClickListener, View.OnTouchListener{

    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;
    private TextView myTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate the gesture detector with the
        // application context and an implementation of
        // GestureDetector.OnGestureListener
        mDetector = new GestureDetectorCompat(this,this);
        // Set the gesture detector as the double tap listener.
        mDetector.setOnDoubleTapListener(this);

        myTextView = (TextView) findViewById(R.id.testo);

        ((ImageView) findViewById(R.id.immagine)).setOnClickListener(this);
        ((ImageView) findViewById(R.id.immagine)).setOnLongClickListener(this);
        ((ImageView) findViewById(R.id.immagine)).setOnTouchListener(this);

    }

    @Override
    public void onClick(View v) {
        Log.d("Evento:","OnClick");
        myTextView.setText("Evento onClick");
    }


    @Override
    public boolean onLongClick(View v) {
        Log.d("Evento:","OnLongClick");
        myTextView.setText("Evento onLongClick");
        return true;
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        this.mDetector.onTouchEvent(event);

        int action = MotionEventCompat.getActionMasked(event);
        // Get the index of the pointer associated with the action.
        int index = MotionEventCompat.getActionIndex(event);
        int xPos = -1;
        int yPos = -1;

        Log.d("Azione:", "The action is " + actionToString(action));
        myTextView.setText(actionToString(action));

        if (event.getPointerCount() > 1) {
            Log.d("Azione:", "Multitouch event");
            // The coordinates of the current screen contact, relative to
            // the responding View or Activity.
            xPos = (int) MotionEventCompat.getX(event, index);
            yPos = (int) MotionEventCompat.getY(event, index);

        } else {
            // Single touch event
            Log.d("Azione:", "Single touch event");
            xPos = (int) MotionEventCompat.getX(event, index);
            yPos = (int) MotionEventCompat.getY(event, index);
        }
        return true;//super.onTouchEvent(event);
    }


    // Given an action int, returns a string description
    public static String actionToString(int action) {
        switch (action) {

            case MotionEvent.ACTION_DOWN: return "Down";
            case MotionEvent.ACTION_MOVE: return "Move";
            case MotionEvent.ACTION_POINTER_DOWN: return "Pointer Down";
            case MotionEvent.ACTION_UP: return "Up";
            case MotionEvent.ACTION_POINTER_UP: return "Pointer Up";
            case MotionEvent.ACTION_OUTSIDE: return "Outside";
            case MotionEvent.ACTION_CANCEL: return "Cancel";
        }
        return "";
    }

    @Override
    public boolean onDown(MotionEvent event) {
        Log.d(DEBUG_TAG,"onDown: " + event.toString());
        myTextView.setText("onDown");
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
        myTextView.setText("onFling");
        return true;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onLongPress: " + event.toString());
        myTextView.setText("onLongPress");
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX,
                            float distanceY) {
        Log.d(DEBUG_TAG, "onScroll: " + event1.toString() + event2.toString());
        myTextView.setText("onScroll");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent event) {
        Log.d(DEBUG_TAG, "onShowPress: " + event.toString());
        myTextView.setText("onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapUp: " + event.toString());
        myTextView.setText("onSingleTapUp");
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTap: " + event.toString());
        myTextView.setText("onDoubleTap");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        Log.d(DEBUG_TAG, "onDoubleTapEvent: " + event.toString());
        myTextView.setText("onDoubleTapEvent");
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + event.toString());
        myTextView.setText("onSingleTapConfirmed");
        return true;
    }
}



