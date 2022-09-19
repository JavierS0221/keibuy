// Deny drag image
$('.deny-drag').on('dragstart', function(event) { event.preventDefault(); });

window.addEventListener('load', () => {
    sleep(500).then(()=>{
    const loaderContainer = document.querySelector('.loader-container');
    loaderContainer.style.opacity = 0;
    loaderContainer.style.visibility = 'hidden'
        sleep(1000).then(() =>{
            loaderContainer.remove()
        })
    })
})

function sleep(ms){
    return new Promise( resolver => setTimeout(resolver, ms));
}

