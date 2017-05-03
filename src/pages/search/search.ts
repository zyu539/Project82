import { Component, OnInit } from '@angular/core';
import { NavController } from 'ionic-angular';
import { ViewController } from 'ionic-angular';
import { Geolocation } from '@ionic-native/geolocation';

declare var google;

@Component({
    selector: 'page-search',
    templateUrl: 'search.html'
})

export class SearchPage implements OnInit {

    autocompleteItems: any;
    autocomplete: any;
    acService: any;
    placesService: any;
    bounds: any;
    constructor(public navCtrl: NavController, public viewCtrl: ViewController, public geolocation: Geolocation) {

        this.acService = new google.maps.places.AutocompleteService();
        this.geolocation.getCurrentPosition().then((position) => {

            var geolocation = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };
            var circle = new google.maps.Circle({
                center: geolocation,
                radius: position.coords.accuracy
            });
            this.bounds = circle.getBounds();
        }, (err) => {
            console.log(err);
        });

    }

    ngOnInit() {

        this.autocompleteItems = [];
        this.autocomplete = {
            query: ''
        }
    }

    ionViewDidLoad() {
    }

    dismiss() {
        this.viewCtrl.dismiss();
    }

    chooseItem(item: any) {
        console.log('modal > chooseItem > item > ', item);
        this.viewCtrl.dismiss(item);
    }

    updateSearch() {
        console.log('modal > updateSearch');
        if (this.autocomplete.query == '') {
            this.autocompleteItems = [];
            return;
        }
        let self = this;
        let config = {
            types: ['geocode'],
            input: this.autocomplete.query,
            bounds: this.bounds,
        }
        this.acService.getPlacePredictions(config, function (predictions, status) {
            console.log('modal > getPlacePredictions > status > ', status);
            self.autocompleteItems = [];
            if (predictions != null) {
                predictions.forEach(function (prediction) {
                    self.autocompleteItems.push(prediction);
                });
            }
        });
    }
}
