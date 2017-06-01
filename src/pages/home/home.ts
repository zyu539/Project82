import { Component, ViewChild, ElementRef } from '@angular/core';
import { NavController, ModalController } from 'ionic-angular';
import { Geolocation } from '@ionic-native/geolocation';
import { SearchPage } from '../search/search';
import * as $ from 'jquery';
import * as xml2js from 'xml2js';
import * as moment from 'moment';

declare var google;

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})


export class HomePage {

  @ViewChild('map') mapElement: ElementRef;

  map: any;

  placesService: any;
  //markers = [];

  interval: any;
  positions = [];
  interval_time = 2000;

  constructor(public navCtrl: NavController, public geolocation: Geolocation, public modalCtrl: ModalController) {

  }

  ionViewDidLoad() {
    this.loadMap();
  }

  showModal() {

    let modal = this.modalCtrl.create(SearchPage);
    modal.onDidDismiss(data => {
      console.log('page > modal dismissed > data > ', data);
      if (data) {
        this.getPlaceDetail(data.place_id);
        this.getInterval();
      }
    })
    modal.present();
  }

  private getInterval() {
    let self = this;
    $.ajax({
      url : "http://localhost:8080/server/route/period",
      type: 'GET',
      success: function(data) {
        self.startRecord(data);
      },

      error: function() {
        console.log('lolololol');
      }
    })
  }

  private startRecord(data) {
    var self = this;
    self.interval_time = data;
    this.interval = setInterval(function () {
      self.geolocation.getCurrentPosition().then((position) => {
        let pos = {
          latitude: position.coords.latitude,
          longitude: position.coords.longitude,
          time: moment().format()
        }
        self.positions.push(pos);
      });

      //testing
      //console.log(self.positions.length);
      //console.log(self.positions[self.positions.length - 1].lat);
      //console.log(self.positions[self.positions.length - 1].lng);

    }, self.interval_time);
  }

  stopRecord() {
    clearInterval(this.interval);
    let position = JSON.parse(JSON.stringify(this.positions));
    let routeData = {
      positions: {position: position},
      period: this.interval_time
    };

    let builder = new xml2js.Builder();
    let xml = builder.buildObject(routeData);
    xml = xml.replace("root", "route");
    xml = xml.replace("root", "route");
    console.log(xml);

    $.ajax({
      url : "http://localhost:8080/server/route/data",
      type: 'POST',
      contentType: "application/xml",
      data: xml,
      success: function() {
        console.log('success');
      },

      error: function() {
        console.log('fail');
      }
    });
  }

  loadMap() {

    this.geolocation.getCurrentPosition().then((position) => {

      let latLng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);

      let mapOptions = {
        center: latLng,
        zoom: 15,
        mapTypeId: google.maps.MapTypeId.ROADMAP
      }

      this.map = new google.maps.Map(this.mapElement.nativeElement, mapOptions);

    }, (err) => {
      console.log(err);
    });

  }

  private getPlaceDetail(place_id: string): void {
    var self = this;
    var request = {
      placeId: place_id
    };
    this.placesService = new google.maps.places.PlacesService(this.map);
    this.placesService.getDetails(request, callback);
    function callback(place, status) {
      if (status == google.maps.places.PlacesServiceStatus.OK) {
        console.log('page > getPlaceDetail > place > ', place);
        // set place in map
        self.map.setCenter(place.geometry.location);
        var placeLoc = place.geometry.location;
        var marker = new google.maps.Marker({
          map: self.map,
          position: placeLoc
        });

        //self.markers.push(marker);

        var infowindow = new google.maps.InfoWindow();
        google.maps.event.addListener(marker, 'click', function () {
          infowindow.setContent('<div><strong>' + place.name + '</strong><br>' +
            'Place ID: ' + place.place_id + '<br>' +
            place.formatted_address + '</div>');
          infowindow.open(self.map, this);
        });

      } else {
        console.log('page > getPlaceDetail > status > ', status);
      }
    }
  }

}
