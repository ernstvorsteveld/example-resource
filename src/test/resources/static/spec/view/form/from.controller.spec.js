describe("Form controller test.", function () {

    var controller;
    var $httpBackend;
    var $scope;

    var expectedUrl = '/persons';
    var responseData = [ {'test': 'val1'}];

    beforeEach(function () {
        module('app');
    });

    beforeEach(inject(function ($controller, $rootScope, _$httpBackend_) {
         controller = $controller('formController', {
             $scope : $rootScope.$new()
         });
         $httpBackend = _$httpBackend_;
     }));

     afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
     });

    it("Should have default value for username.", function () {
        expect(controller.username).toBe('username1');
    });

    it("Should load all.", function () {
        $httpBackend.expect("GET", expectedUrl).respond(200, responseData);
        controller.loadAll();
        $httpBackend.flush();
        expect(controller.persons.test).toBe(responseData.test);
    });

});
