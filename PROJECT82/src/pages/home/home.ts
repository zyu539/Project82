import { Component, ViewChild, ElementRef } from '@angular/core';
import { NavController } from 'ionic-angular';
import { Geolocation } from '@ionic-native/geolocation';
import {ContactPage} from '../contact/contact';
declare var google;

@Component({
  selector: 'home-page',
  templateUrl: 'home.html'
})
export class HomePage {

  @ViewChild('map') mapElement: ElementRef;
  static map: any;

  constructor(public navCtrl: NavController, public geolocation: Geolocation) {

  }

  toSearchPage() {
    this.navCtrl.push(ContactPage);
  }

  ionViewDidLoad(){
    this.loadMap();
  }

  addInfoWindow(marker, content){

    let infoWindow = new google.maps.InfoWindow({
      content: content
    });

    google.maps.event.addListener(marker, 'click', () => {
      infoWindow.open(HomePage.map, marker);
    });

  }

  loadMap(){

    this.geolocation.getCurrentPosition().then((position) => {

      let latLng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);

      let mapOptions = {
        center: latLng,
        zoom: 15,
        mapTypeId: google.maps.MapTypeId.ROADMAP
      }

      HomePage.map = new google.maps.Map(this.mapElement.nativeElement, mapOptions);

    }, (err) => {
      console.log(err);
      let latLng = new google.maps.LatLng(-33.8665433, 151.1956316);

      let mapOptions = {
        center: latLng,
        zoom: 15,
        mapTypeId: google.maps.MapTypeId.ROADMAP
      }

      HomePage.map = new google.maps.Map(this.mapElement.nativeElement, mapOptions);
    });

  }

  addMarker(){

    let marker = new google.maps.Marker({
      map: HomePage.map,
      animation: google.maps.Animation.DROP,
      position: HomePage.map.getCenter()
    });

    let content = "<h4>Information!</h4>";

    this.addInfoWindow(marker, content);

  }

}
