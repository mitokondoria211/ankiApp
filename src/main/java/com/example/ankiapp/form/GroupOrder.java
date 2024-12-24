package com.example.ankiapp.form;

import com.example.ankiapp.form.ValidGroups.ValidGroup1;
import com.example.ankiapp.form.ValidGroups.ValidGroup2;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
import jakarta.validation.GroupSequence;

@GroupSequence({ Default.class,ValidGroup1.class, ValidGroup2.class})
public interface GroupOrder {

}
