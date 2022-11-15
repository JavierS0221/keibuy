'use strict';
var stompClient = null;

var personId = null;
var itemId = null;

function connect(event, p, i) {
    personId = p;
    itemId = i;

    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/item/' + itemId + '/offer', function (response) {
            onOfferReceived(JSON.parse(response.body));
        });
        stompClient.subscribe('/item/' + itemId + '/isFinalized', function (response) {
            onFinalize(JSON.parse(response.body));
        });
    }, async function () {
        await new Promise(resolve => setTimeout(resolve, 2000));
        document.location.reload();
    });
}

function onOfferReceived(response) {
    let username = response.username;
    let personId = response.personId;
    let offer = response.offer;
    updatePerson({'id': personId, 'username': username, 'offer': offer});
    refreshData();
}

function onFinalize(response) {
    if(response) {
        console.log("finalizo la subasta");
        if(listParticipants.length > 0) {
            console.log("- Gano "+listParticipants[0].username)
        } else {
            console.log("- No gano nadie")
        }
    }}
