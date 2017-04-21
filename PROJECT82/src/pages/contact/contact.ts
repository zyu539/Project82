import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import { Geolocation } from '@ionic-native/geolocation';
import {HomePage} from '../home/home';
declare var google;

@Component({
  selector: 'page-contact',
  templateUrl: 'contact.html'
})
export class ContactPage {

  static markers;

  constructor(public navCtrl: NavController) {

  }

  ionViewWillEnter() {
    this.autofillAddress();
  }

  autofillAddress() {
    // Create the search box and link it to the UI element.
    var input = document.getElementById('pac-input');
    var searchBox = new google.maps.places.SearchBox(input);
    var ctrl = this.navCtrl;
    // Bias the SearchBox results towards current map's viewport.
    HomePage.map.addListener('bounds_changed', function() {
      searchBox.setBounds(HomePage.map.getBounds());
    });

    ContactPage.markers = [];
    // Listen for the event fired when the user selects a prediction and retrieve
    // more details for that place.
    searchBox.addListener('places_changed', function() {
      var places = searchBox.getPlaces();

      if (places.length == 0) {
        return;
      }

      // Clear out the old markers.
      ContactPage.markers.forEach(function(marker) {
        marker.setMap(null);
      });
      ContactPage.markers = [];

      // For each place, get the icon, name and location.
      var bounds = new google.maps.LatLngBounds();
      places.forEach(function(place) {
        if (!place.geometry) {
          console.log("Returned place contains no geometry");
          return;
        }
        var icon = {
          url: place.icon,
          size: new google.maps.Size(71, 71),
          origin: new google.maps.Point(0, 0),
          anchor: new google.maps.Point(17, 34),
          scaledSize: new google.maps.Size(25, 25)
        };

        // Create a marker for each place.
        ContactPage.markers.push(new google.maps.Marker({
          map: HomePage.map,
          icon: icon,
          title: place.name,
          position: place.geometry.location
        }));

        if (place.geometry.viewport) {
          // Only geocodes have viewport.
          bounds.union(place.geometry.viewport);
        } else {
          bounds.extend(place.geometry.location);
        }
      });
      HomePage.map.fitBounds(bounds);
      ctrl.pop();
    });

  }

}
