(function () {

    var injectParams = ['personFactory'];

    var controller = function (personFactory) {

        var vm = {};

        vm.loadAll = function () {
            vm.persons = personFactory.loadAll();
        };

        vm.loadOne = function () {
            vm.person = personFactory.load(vm.username);
        };

        vm.create = function() {
            personFactory.create(vm.username, vm.firstname, vm.middlename, vm.lastname);
        };

        vm.username = "username1";
        vm.firstname = "";
        vm.middlename = "";
        vm.lastname = "";
        return vm;
    };

    controller.$inject = injectParams;
    angular.module('app').controller('formController', controller);

})();


