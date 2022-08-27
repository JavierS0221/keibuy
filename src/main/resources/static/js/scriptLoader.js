window.addEventListener('load', () => {
    sleep(1).then(()=>{
    const loaderContainer = document.querySelector('.loader-container');
    loaderContainer.style.opacity = 0;
    loaderContainer.style.visibility = 'hidden'
    })
})

function sleep(ms){
    return new Promise( resolver => setTimeout(resolver, ms));
};

