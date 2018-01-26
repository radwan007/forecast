package com.parent.forecast.utils;

import android.content.SharedPreferences;

import java.util.Locale;

public class UnitConvertor {
    public static float convertTemperature(float temperature, SharedPreferences sp) {
        if (sp.getString("unit", "°C").equals("°C")) {
            return UnitConvertor.kelvinToCelsius(temperature);
        } else if (sp.getString("unit", "°C").equals("°F")) {
            return UnitConvertor.kelvinToFahrenheit(temperature);
        } else {
            return temperature;
        }
    }

    public static float kelvinToCelsius(float kelvinTemp) {
        return kelvinTemp - 273.15f;
    }

    public static float kelvinToFahrenheit(float kelvinTemp) {
        return (((9 * kelvinToCelsius(kelvinTemp)) / 5) + 32);
    }

    public static String getRainString(double rain, SharedPreferences sp) {
        if (rain > 0) {
            if (sp.getString("lengthUnit", "mm").equals("mm")) {
                if (rain < 0.1) {
                    return " (<0.1 mm)";
                } else {
                    return String.format(Locale.ENGLISH, " (%.1f %s)", rain, sp.getString("lengthUnit", "mm"));
                }
            } else {
                rain = rain / 25.4;
                if (rain < 0.01) {
                    return " (<0.01 in)";
                } else {
                    return String.format(Locale.ENGLISH, " (%.2f %s)", rain, sp.getString("lengthUnit", "mm"));
                }
            }
        } else {
            return "";
        }
    }

    public static float convertPressure(float pressure, SharedPreferences sp) {
        if (sp.getString("pressureUnit", "hPa").equals("kPa")) {
            return pressure / 10;
        } else if (sp.getString("pressureUnit", "hPa").equals("mm Hg")) {
            return (float) (pressure * 0.750061561303);
        } else if (sp.getString("pressureUnit", "hPa").equals("in Hg")) {
            return (float) (pressure * 0.0295299830714);
        } else {
            return pressure;
        }
    }
}
