(function () {

    var app = angular.module('app', ['ngRoute', 'ngResource']);

    app.constant("URLS", {
        "viewBase": "app/view/",
        "controllers": {
            "persons": '/persons/:username'
        }
    });

    app.config(['$routeProvider', "URLS", function ($routeProvider, URLS) {
        $routeProvider
            .when('/form', {
                controller: 'formController',
                templateUrl: URLS.viewBase + 'form/form.html',
                controllerAs: 'form'
            })
            .otherwise({redirectTo: '/form'});
    }]);

}());
