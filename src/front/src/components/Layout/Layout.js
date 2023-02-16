import { Fragment } from 'react';

import MainNavigation from './MainNavigation';

const Layout = ({children}) => {
  return (
    <Fragment>
      <MainNavigation />
      <main>{children}</main>
    </Fragment>
  );
};

export default Layout;
