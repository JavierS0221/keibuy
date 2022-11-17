'use strict';
var stompClient = null;

var personId = null;
var itemId = null;

var personIdWin = null;

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
    let offer = response.offer;
    updatePerson({'id': response.personId, 'username': username, 'offer': offer});
    refreshData();

    personIdWin = response.personId;
}

function onFinalize(response) {
    if (response) {
        console.log("finalizo la subasta");
        if (listParticipants.length > 0) {
            let isParticipant = false;
            for(let x of listParticipants) {
                if(x.id === personId) {
                    isParticipant = true;
                    break;
                }
            }


            var winModal = new bootstrap.Modal(
                document.getElementById("winModal"),
                {
                    keyboard: false,
                }
            );
            var lostModal = new bootstrap.Modal(
                document.getElementById("lostModal"),
                {
                    keyboard: false,
                }
            );
            let hasWin = false;
            if (personIdWin != null) {
                if (personIdWin === personId) {
                    hasWin = true;
                }
            }

            if (hasWin) {
                winModal.show();
            } else {
                winModal.show();
            }
        }

        finishTime();
    }
}
