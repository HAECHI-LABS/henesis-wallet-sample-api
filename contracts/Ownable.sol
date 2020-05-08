pragma solidity 0.6.6;

contract Ownable {
    address private _owner;

    event OwnershipTransferred(
        address indexed currentOwner,
        address indexed newOwner
    );

    constructor() internal {
        _owner = msg.sender;
        emit OwnershipTransferred(address(0), msg.sender);
    }

    modifier onlyOwner() {
        require(
            msg.sender == _owner,
            "Ownable : Function called by unauthorized user."
        );
        _;
    }

    function owner() external view override returns (address ownerAddress) {
        ownerAddress = _owner;
    }

    function transferOwnership(address newOwner)
        external
        override
        onlyOwner
        returns (bool success)
    {
        require(newOwner != address(0), "TransferOwnership/User renounceOwnership() for deleting owner");
        return _transferOwnership(newOwner);
    }

    function renounceOwnership() external override onlyOwner returns (bool success) {
        success = _transferOwnership(address(0));
    }

    function _transferOwnership(address newOwner) internal returns (bool success) {
        emit OwnershipTransferred(_owner, newOwner);
        _owner = newOwner;
        success = true;
    }
}
