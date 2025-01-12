// Fetching weather data from an API
async function getWeather() {
    const response = await fetch('https://api.openweathermap.org/data/2.5/weather?q=London&appid=YOUR_API_KEY');
    const data = await response.json();
    const weatherWidget = document.getElementById('weather-widget');
    weatherWidget.innerHTML = `
        <p>Weather in ${data.name}: ${data.weather[0].description}</p>
        <p>Temperature: ${(data.main.temp - 273.15).toFixed(2)}°C</p>
    `;
}

// Fetching cryptocurrency data
async function getCrypto() {
    const response = await fetch('https://api.coingecko.com/api/v3/simple/price?ids=bitcoin,ethereum,litecoin&vs_currencies=usd');
    const data = await response.json();
    const cryptoWidget = document.getElementById('crypto-widget');
    cryptoWidget.innerHTML = `
        <p>Bitcoin: $${data.bitcoin.usd}</p>
        <p>Ethereum: $${data.ethereum.usd}</p>
        <p>Litecoin: $${data.litecoin.usd}</p>
    `;
}

// Load weather and crypto data on page load
window.onload = () => {
    getWeather();
    getCrypto();
};
