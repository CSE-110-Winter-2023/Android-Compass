package edu.ucsd.cse110.projects110;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import static android.view.View.VISIBLE;

import androidx.constraintlayout.widget.ConstraintLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class US9AND10MS2bddtest {

    @Mock
    private Activity activity;

    @Mock
    private SharedPreferences preferences;

    @Mock
    private TextView counter;

    @Mock
    private ConstraintLayout circle1Layout;

    @Mock
    private ConstraintLayout circle2Layout;

    @Mock
    private ConstraintLayout circle3Layout;

    @Mock
    private ConstraintLayout circle4Layout;

    @Mock
    private ConstraintLayout.LayoutParams circle1LayoutParams;

    @Mock
    private ConstraintLayout.LayoutParams circle2LayoutParams;

    @Mock
    private ConstraintLayout.LayoutParams circle3LayoutParams;

    @Mock
    private ConstraintLayout.LayoutParams circle4LayoutParams;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(activity.getSharedPreferences("pref", Context.MODE_PRIVATE)).thenReturn(preferences);

        when(activity.findViewById(R.id.counter)).thenReturn(counter);
        when(activity.findViewById(R.id.Circle1Constraint)).thenReturn(circle1Layout);
        when(activity.findViewById(R.id.Circle2Constraint)).thenReturn(circle2Layout);
        when(activity.findViewById(R.id.Circle3Constraint)).thenReturn(circle3Layout);
        when(activity.findViewById(R.id.Circle4Constraint)).thenReturn(circle4Layout);

        when(circle1Layout.getLayoutParams()).thenReturn(circle1LayoutParams);
        when(circle2Layout.getLayoutParams()).thenReturn(circle2LayoutParams);
        when(circle3Layout.getLayoutParams()).thenReturn(circle3LayoutParams);
        when(circle4Layout.getLayoutParams()).thenReturn(circle4LayoutParams);
    }

    @Test
    public void testDisplayZoomZero() {

        when(preferences.getInt("zoomFactor", 0)).thenReturn(0);

        ZoomDisplay.displayZoom(activity);

        assertTrue(circle1Layout.getVisibility() == View.VISIBLE);
        assertTrue(circle2Layout.getVisibility() == View.VISIBLE);
        assertTrue(circle3Layout.getVisibility() == View.VISIBLE);
        assertTrue(circle4Layout.getVisibility() == View.VISIBLE);

        assertEquals(500, circle1LayoutParams.width);
        assertEquals(500, circle1LayoutParams.height);
        assertEquals(700, circle2LayoutParams.width);
        assertEquals(700, circle2LayoutParams.height);
        assertEquals(900, circle3LayoutParams.width);
        assertEquals(900, circle3LayoutParams.height);
        assertEquals(1100, circle4LayoutParams.width);
        assertEquals(1100, circle4LayoutParams.height);
    }

    @Test
    public void testDisplayZoomThree() {
        when(preferences.getInt("zoomFactor", 0)).thenReturn(3);

        ZoomDisplay.displayZoom(activity);

        // Check that only the first circle is visible
        assertEquals(VISIBLE, circle1Layout.getVisibility());
        assertEquals(VISIBLE, circle2Layout.getVisibility());
        assertEquals(VISIBLE, circle3Layout.getVisibility());
        assertEquals(VISIBLE, circle4Layout.getVisibility());

        // Check that the layout parameters of the first circle are set correctly
        assertEquals(1025, circle1LayoutParams.width);
        assertEquals(1025, circle1LayoutParams.height);

    }



}
