/*
    CanZE
    Take a closer look at your ZE car

    Copyright (C) 2015 - The CanZE Team
    http://canze.fisch.lu

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or any
    later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package lu.fisch.canze.activities;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

import lu.fisch.canze.R;
import lu.fisch.canze.classes.Sid;
import lu.fisch.canze.actors.Field;
import lu.fisch.canze.interfaces.DebugListener;
import lu.fisch.canze.interfaces.FieldListener;

public class ZE50TestActivity extends CanzeActivity implements FieldListener, DebugListener {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fieldtest);
    }

    protected void initListeners() {
        MainActivity.getInstance().setDebugListener(this);
        //addField(Sid.GW3, 2000);

        addField(Sid.EVC, 2000);
        addField(Sid.Pedal);
        addField(Sid.TorqueRequest);
    }

    // This is the event fired as soon as this the registered fields are
    // getting updated by the corresponding reader class.
    @Override
    public void onFieldUpdateEvent(final Field field) {
        // the update has to be done in a separate thread
        // otherwise the UI will not be repainted
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String fieldId = field.getSID();
                double val = field.getValue();
                TextView tv = null;
                ProgressBar pb = null;

                // get the text field
                switch (fieldId) {
                    case Sid.Pedal:
                        tv = findViewById(R.id.tv_test_11);
                        pb = null;
                        break;

                    case Sid.TorqueRequest:
                        tv = findViewById(R.id.tv_test_12);
                        pb = null;
                        break;
                }
                // set regular new content, all exceptions handled above
                if (tv != null) {
                    tv.setText(String.format(Locale.getDefault(), "%s:%." + field.getDecimals() + "f%s\n", field.getName(), field.getValue(), field.getUnit()));
                }
                if (pb != null) {
                    pb.setProgress((int)(val));
                }


            }
        });

    }

}