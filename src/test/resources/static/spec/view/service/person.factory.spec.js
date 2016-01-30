describe("Person factory tests.", function () {

    var $httpBackend;
    var service;
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

    beforeEach(inject(function (_$httpBackend_, personFactory) {
         $httpBackend = _$httpBackend_;
         service = personFactory;
     }));

     beforeEach(function () {
     });

     afterEach(function() {
        $httpBackend.verifyNoOutstandingExpectation();
        $httpBackend.verifyNoOutstandingRequest();
     });


    it("Should load all persons.", function () {
        $httpBackend.expect("GET", baseUrl).respond(200, responseData);
        var persons = service.loadAll();
        $httpBackend.flush();
        expect(persons[0].username).toBe("username1");
        expect(persons[1].username).toBe("username2");
    });

    it("Should load one person by username.", function () {
        $httpBackend.expect("GET", baseUrl + '/username1').respond(200, responseData[0]);
        var person = service.load("username1");
        $httpBackend.flush();
        expect(person.username).toBe("username1");

        $httpBackend.expect("GET", baseUrl + '/username2').respond(200, responseData[1]);
        person = service.load("username2");
        $httpBackend.flush();
        expect(person.username).toBe("username2");
    });

    it("Should create new user and make get it.", function () {
        var person = {
            username: "username3",
            firstname : "firstname3",
            middlename : "middlename3",
            lastname : "lastname3"
        };
        $httpBackend.expect("POST", baseUrl).respond(200, person);
        service.create('username3', 'firstname3', 'middlename3', 'lastname3');
        $httpBackend.flush();
        $httpBackend.expect("GET", baseUrl + '/username3').respond(200, person);
        person = service.load("username3");
        $httpBackend.flush();
        expect(person.username).toBe("username3");
    });
});
