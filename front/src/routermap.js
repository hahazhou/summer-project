import React ,{Component}from 'react'
import {BrowserRouter,Route} from 'react-router-dom'
import Home from './home'
import Admin from "./admin";
import Login from "./login"

class RouterMap extends Component {
    render() {
        return (
            <BrowserRouter>
            <div>
                <Route path={"/"} exact component={Home}/>
                <Route path={"/home"} component={Home} />
                <Route path={"/admin"} component={Admin}/>
                <Route path={"/login"} component={Login}/>
            </div>
            </BrowserRouter>
        )

    }
}

export default RouterMap