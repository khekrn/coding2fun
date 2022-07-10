# Setup

- Install express module using `npm install express`
- Install **vegata** load testing tool using `brew update && brew install vegeta`
- Run the **index.js** under node-server folder using `node node-server/index.js`
- Build the one of Sever module(Blocking/Coroutine) then execute the Jar
- For benchmarking coroutine run `vegeta attack -duration=60s -rate=30 -targets=reactor.list -output=reactor.bin`
- For benchmarking thread per requests `vegeta attack -duration=60s -rate=30 -targets=blocking.list -output=blocking.bin`
- You can view the results using `vegeta report blocing.bin` or `vegeta report reactor.bin`