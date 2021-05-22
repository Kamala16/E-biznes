export default async function getData(url: string, method: string, data?: JSON) {
    const result = await fetch(url, {
        mode: 'cors',
        headers: {
            'Accept' : 'application/json',
            'Content-type': 'application/json',
            'Access-Control-Allow-Origin': 'http://localhost:3000',
        },
        method: method,
        body: data ? JSON.stringify(data) : undefined
    })
    return await result.json();
}