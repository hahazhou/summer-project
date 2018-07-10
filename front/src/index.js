import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import registerServiceWorker from './registerServiceWorker';
import RouterMap from "./routermap";

ReactDOM.render(<RouterMap />, document.getElementById('root'));
registerServiceWorker();
