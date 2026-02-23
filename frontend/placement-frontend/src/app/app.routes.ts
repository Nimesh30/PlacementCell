import { Routes } from '@angular/router';
import { Home } from './home/home';
import { Register } from './auth/register/register';
import { Login } from './auth/login/login';
import { ChangePassword } from './auth/change-password/change-password';

import { UserDashboard } from './user/user-dashboard/user-dashboard';
import { Myprofile } from './user/myprofile/myprofile';
import { Joblistings } from './user/joblistings/joblistings';
import { Myapplications } from './user/myapplications/myapplications';
import { Noticeboard } from './user/noticeboard/noticeboard';
import { Layout } from './layout/layout';

export const routes: Routes = [

  { path: '', component: Home },
  { path: 'register', component: Register },
  { path: 'login', component: Login },
  { path: 'change-password', component: ChangePassword },
  // ✅ IMPORTANT PART
  {
    path: 'layout',
    component: Layout,
    children: [
      { path: 'userdashboard', component: UserDashboard },
      { path: 'profile', component: Myprofile },
      { path: 'jobs', component: Joblistings },
      { path: 'applications', component: Myapplications },
      { path: 'notice', component: Noticeboard },
    ]
  },

  { path: '**', redirectTo: '' }

];