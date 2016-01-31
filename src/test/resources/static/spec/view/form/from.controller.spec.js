describe("Form controller test.", function () {

    var controller;
    var $httpBackend;
    var $scope;

    var baseUrl = '/persons';
    var responseData = [
        {
            username: "username1",
            firstname : "firstname1",
            middlename : "middlename1",
            lastname : "lastname1"
        },
        {
            username: "username2",
            firstname : "firstname2",
            middlename : "middlename2",
            lastname : "lastname2"
        }
    ];

    beforeEach(function () {
        module('app');
    });

    beforeEach(inject(function ($controller, _$httpBackend_) {
         controller = $controller('formController', {});
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
        $httpBackend.expect("GET", baseUrl).respond(200, responseData);
        controller.loadAll();
        $httpBackend.flush();
        expect(controller.persons.test).toBe(responseData.test);
    });

    it("Should load one person by username.", function () {
        $httpBackend.expect("GET", baseUrl + '/username1').respond(200, responseData[0]);
        var person = controller.loadOne('username1');
        $httpBackend.flush();
        expect(controller.username).toBe("username1");
    });

    it("Should create person, and is loadable.", function () {
        var person = {
            username: "username3",
            firstname : "firstname3",
            middlename : "middlename3",
            lastname : "lastname3"
        };

        controller.username = 'username3';
        controller.firstname = 'firstname3';
        controller.middlename = 'middlename3';
        controller.lastname = 'lastname3';
        $httpBackend.expect("POST", baseUrl).respond(200, person);
        controller.create();
        $httpBackend.flush();
        $httpBackend.expect("GET", baseUrl + '/username3').respond(200, person);
        person = controller.loadOne('username3');
        $httpBackend.flush();
        expect(controller.username).toBe("username3");
    });

});
