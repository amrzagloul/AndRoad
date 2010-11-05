package org.andnav2.sys.ors.ds.yahoo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.andnav2.sys.ors.ds.DSRequester;

import org.andnav2.osm.adt.GeoPoint;
import org.andnav2.sys.ors.adt.ds.ORSPOI;
import org.andnav2.sys.ors.adt.ds.POIType;
import org.andnav2.sys.ors.exceptions.ORSException;
import org.andnav2.sys.ors.util.Util;
import org.andnav2.util.constants.Constants;

import android.content.Context;

public class YahooDSRequester implements Constants, DSRequester {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

    public YahooDSRequester() {
    }

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	/**
	 * @param gp
	 * @param pRadiusMeters
	 * @throws ORSException
	 */
	public ArrayList<ORSPOI> request(final Context ctx, final GeoPoint aGeoPoint, final POIType aPOIType, final int pRadiusMeters) throws IOException, ORSException {
		final URL requestURL = new URL("http://maps.yahoo.com/services/local/rals?" + YahooDSRequestComposer.create(ctx, aGeoPoint, aPOIType, pRadiusMeters));

		final HttpURLConnection acon = (HttpURLConnection) requestURL.openConnection();
		acon.setAllowUserInteraction(false);
		acon.setRequestMethod("GET");
		acon.setRequestProperty("Content-Type", "application/xml");
		acon.setDoOutput(true);
		acon.setDoInput(true);
		acon.setUseCaches(false);
        String result = Util.convertStreamToString(acon.getInputStream());

		final YahooDSParser openDSParser = new YahooDSParser();
		/* The Handler now provides the parsed data to us. */
		return openDSParser.getDSResponse(result, aPOIType);
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}