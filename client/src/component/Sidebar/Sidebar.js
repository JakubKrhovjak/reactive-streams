import PropTypes from 'prop-types'
import React from "react";
import Drawer from "@material-ui/core/Drawer";

const Sidebar = ({ children, ...props }) => {
    return (
        <Drawer {...props}>
            {children}
        </Drawer>
    );
};

Sidebar.propTypes = {
    children: PropTypes.node.isRequired,
    open: PropTypes.bool.isRequired
};

export default Sidebar;
