package com.konduto.sdk.models;

import com.google.gson.JsonObject;
import com.konduto.sdk.factories.KondutoPassengerFactory;
import com.konduto.sdk.factories.KondutoTravelLegFactory;
import com.konduto.sdk.utils.TestUtils;
import org.junit.Test;

import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by rsampaio on 07/05/15.
 *
 */
public class KondutoTravelTest {
    private static final KondutoTravel TRAVEL = new KondutoTravel();

    {
        try {
            TRAVEL.setDepartureLeg(KondutoTravelLegFactory.departureFlight());
            TRAVEL.setReturnLeg(KondutoTravelLegFactory.returnFlight());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TRAVEL.setPassengers(KondutoPassengerFactory.passengersList());
        TRAVEL.setTravelType(KondutoTravelType.FLIGHT);
    }

    private static final JsonObject TRAVEL_JSON = (JsonObject) TestUtils.readJSONFromFile("travel.json");


    @Test
    public void isValidTest() throws Exception {
        KondutoTravel travel = new KondutoTravel();
        // is invalid without type
        assertFalse(travel.isValid());
        travel.setTravelType(KondutoTravelType.FLIGHT);
        // is invalid without departure leg
        assertFalse(travel.isValid());
        KondutoFlightTravelLeg departureLeg = KondutoTravelLegFactory.departureFlight();
        travel.setDepartureLeg(departureLeg);
        // is invalid without passengers
        assertFalse(travel.isValid());
        List<KondutoPassenger> passengers = KondutoPassengerFactory.passengersList();
        travel.setPassengers(passengers);
        assertTrue(travel.isValid());
        // is valid with return leg
        KondutoFlightTravelLeg returnLeg = KondutoTravelLegFactory.returnFlight();
        travel.setReturnLeg(returnLeg);
        assertTrue(travel.isValid());
    }

    @Test
    public void serializeTest() throws Exception {
        assertEquals(TRAVEL.toJSON(), TRAVEL_JSON);
    }

    @Test
    public void deserializeTest() throws Exception {
        assertEquals(KondutoModel.fromJSON(TRAVEL_JSON, KondutoTravel.class), TRAVEL);
    }



}
