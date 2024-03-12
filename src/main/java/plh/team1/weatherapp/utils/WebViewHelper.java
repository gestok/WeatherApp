package plh.team1.weatherapp.utils;

public class WebViewHelper {
    
    /**
     * Method to call when a city is selected. The method populates a web view
     * of a map with the help of OpenLayers library.
     *
     * @param latitude
     * @param longitude
     * @return String
     */
    public static String formatMapHtml(double latitude, double longitude) {
        int zoom = 12;
        String htmlContent = "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<link rel=\"stylesheet\" href=\"https://openlayers.org/en/v4.6.5/css/ol.css\" type=\"text/css\">"
                + "<style>"
                + "  body {"
                + "    margin: 0;"
                + "    background: #ebfbfe;"
                + "  }"
                + "  .map {"
                + "    height: 100vh;"
                + "    width: 100%;"
                + "  }"
                + "  canvas {"
                + "    border-radius: 14px;"
                + "  }"
                + "</style>"
                + "<script src=\"https://openlayers.org/en/v4.6.5/build/ol.js\" type=\"text/javascript\"></script>"
                + "</head>"
                + "<body>"
                + "<div id=\"map\" class=\"map\"></div>"
                + "<script type=\"text/javascript\">"
                + "  var map = new ol.Map({"
                + "    target: 'map',"
                + "    layers: ["
                + "      new ol.layer.Tile({"
                + "        source: new ol.source.OSM()"
                + "      })"
                + "    ],"
                + "    view: new ol.View({"
                + "      center: ol.proj.fromLonLat([" + longitude + ", " + latitude + "]),"
                + "      zoom: " + zoom
                + "    }),"
                + "    controls: ol.control.defaults({"
                + "      attributionOptions: ({"
                + "        collapsible: false"
                + "      }),"
                + "      zoom: false," // Disable default zoom controls
                + "    }),"
                + "    interactions: ol.interaction.defaults({"
                + "      mouseWheelZoom: false,"
                + "      dragPan: false,"
                + "      doubleClickZoom: false"
                + "    })"
                + "  });"
                // Marker
                + "  var marker = new ol.Feature({"
                + "    geometry: new ol.geom.Point("
                + "      ol.proj.fromLonLat([" + longitude + ", " + latitude + "])"
                + "    )"
                + "  });"
                + "  var vectorSource = new ol.source.Vector({"
                + "    features: [marker]"
                + "  });"
                + "  var markerVectorLayer = new ol.layer.Vector({"
                + "    source: vectorSource,"
                + "  });"
                // Disable right-click context menu
                + "  document.getElementById('map').addEventListener('contextmenu', function(evt) {"
                + "    evt.preventDefault();"
                + "  });"
                + "  map.addLayer(markerVectorLayer);"
                + "</script>"
                + "</body>"
                + "</html>";

        return htmlContent;
    }
}
