import { Routes } from '@angular/router';
import { Home } from './home/home';
import { Register } from './Components/register/register';
import { Login } from './Components/login/login';
import { ChangePassword } from './Components/change-password/change-password';
import { UserDashboard } from './user-dashboard/user-dashboard';

export const routes: Routes = [

    {path:'',component:Home},
    {path:'register',component:Register},
    {path:'login',component:Login},
    {path:'change-password',component:ChangePassword},
    {path:'userdashboard',component:UserDashboard}
];
