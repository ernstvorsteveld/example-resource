(function () {

    var injectParams = ['$resource', "URLS"];

    var factory = function ($resource, URLS) {

        var Person = $resource(URLS.controllers.persons, {}, {
            get: {method: 'GET', params: {username: '@userName'}},
            create: {method: 'POST', params: {}}
        });

        var vm = {};
        vm.loadAll = function () {
            return Person.query();
        };

        vm.load = function (username) {
            return Person.get({username: username});
        };

        vm.create = function (username, firstname, middlename, lastname) {
            var newPerson = new Person();
            newPerson.username = username;
            newPerson.firstname = firstname;
            newPerson.middlename = middlename;
            newPerson.lastname = lastname;
            newPerson.$save();
        };

        return vm;
    };

    factory.$inject = injectParams;
    angular.module('app').factory('personFactory', factory);

})();


